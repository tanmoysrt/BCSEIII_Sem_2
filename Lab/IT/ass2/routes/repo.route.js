const router = require("express").Router();
const prisma = require("../db").getInstance();
const multer  = require('multer')
const fs  = require("fs");
const JSZip = require('jszip');

const upload = multer({ dest: 'uploads/' })

// Show repo details
router.get("/:username/:reponame", async(req, res) => {
    const username = req.params["username"];
    const reponame = req.params["reponame"];
    // Show private repo
    let showPrivateContent = false;
    if(req.is_authenticated){
        if(req.user.username !== username) showPrivateContent = true;
    }
    // Verify user in case of private repo
    const repo_details = await prisma.repository.findFirst({
        where: {
            user: {
                username: username
            },
            name: reponame
        },
        select: {
            isPrivate: true
        }
    })
    if(repo_details == null){
        return res.status(404).send("Repo not found");
    }
    if(repo_details.isPrivate){
        if(!req.is_authenticated){
            return res.status(403).send("Not authorized");
        }
        if(req.user.username !== username){
            return res.status(403).send("Not authorized");
        }
    }

    const repo = await prisma.repository.findMany({
        where: {
            user:{
                username: username
            },
            name: reponame
        },
        select: {
            id: true,
            name: true,
            isPrivate: true,
            user: {
                select: {
                    username: true
                }
            },
            content: {
                select: {
                    id: true,
                    isImage: true,
                    imageName: true,
                    imagePath: true,
                    text: true
                }
            }
        }
    })

    let showEditForm = false;
    if(req.is_authenticated){
        if(req.user.username == username) showEditForm = true;
    }
    res.render("repo", {...repo['0'], showEditForm});
})

const cpUpload = upload.fields([{ name: 'image_content', maxCount: 1 }])
// Handle new content submission (POST)
router.post("/:username/:reponame", cpUpload, async (req, res) => {
    const username = req.params["username"];
    const reponame = req.params["reponame"];
    if(req.user.username != username) return res.redirect(`/${username}/${reponame}`);
    const action = req.body["action"];
    console.log("HH")
    if(action == "submit_content"){
        const content_type = req.body["content_type"];
        if(content_type == "text"){
            await prisma.content.create({
                data: {
                    repository: {
                        connect: {
                            name: reponame
                        }
                    },
                    isImage: false,
                    text: req.body["text_content"]
                }
            })
        }else if(content_type == "image"){
            const file = req.files.image_content[0];
            await prisma.content.create({
                data: {
                    repository: {
                        connect: {
                            name: reponame
                        }
                    },
                    isImage: true,
                    imageName: file["originalname"],
                    imagePath: file["path"]
                }
            })

        }
    }else if(action == "make_private"){
        await prisma.repository.updateMany({
            where: {
                name: reponame
            },
            data: {
                isPrivate: true
            }
        })
    }else if(action == "make_public"){
        await prisma.repository.updateMany({
            where: {
                name: reponame
            },
            data: {
                isPrivate: false
            }
        })
    }
    res.redirect(`/${username}/${reponame}`)
});

router.get("/:username/:reponame/downloadall", async(req, res)=>{
    const username = req.params["username"];
    const reponame = req.params["reponame"];
    // Show private repo
    let isPrivateAccessible = false;
    if(req.is_authenticated){
        if(req.user.username !== username) isPrivateAccessible = true;
    }
    const ccontent = await prisma.content.findMany({
        where: {
            repository: {
                name: reponame,
                user: {
                    username: username
                }
            }
        },
        select: {
            imagePath: true,
            imageName: true,
            isImage: true,
            text: true,
        }
    })

    const zip = new JSZip();
    for(let i=0; i<ccontent.length; i++){
        if(ccontent[i].isImage){
            zip.file(ccontent[i].imageName, fs.readFileSync(__basedir+"/"+ccontent[i].imagePath));
        }else{
            zip.file("text_"+i+".txt", ccontent[i].text);
        }
    }
    res.setHeader('Content-disposition', 'attachment; filename='+reponame+'.zip');
    zip.generateNodeStream({type:"nodebuffer", streamFiles: true})
    .pipe(res)
})

router.get("/:username/:reponame/:contentid", async(req, res)=>{
    const username = req.params["username"];
    const reponame = req.params["reponame"];
    // Show private repo
    let isPrivate = false;
    if(req.is_authenticated){
        if(req.user.username !== username) isPrivate = true;
    }
    const contentid = req.params["contentid"];
    const content = await prisma.content.findFirstOrThrow({
        where: {
            id: parseInt(contentid),
            repository: {
                name: reponame
            },
            isImage: true
        },
        select: {
            imageName: true,
            imagePath: true
        }
    })
    fs.readFile(__basedir+"/"+content.imagePath, function (err, data) {
        if (err) {
          throw err;
        }
        res.setHeader("content-disposition", "attachment; filename="+content.imageName);
        res.send(data)
      });

})




module.exports = router;