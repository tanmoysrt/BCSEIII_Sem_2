const router = require("express").Router();
const prisma = require("../db").getInstance();
const multer  = require('multer')
const fs  = require("fs");
const upload = multer({ dest: 'uploads/' })

// Show repo details
router.get("/:username/:reponame", async(req, res) => {
    const username = req.params["username"];
    const reponame = req.params["reponame"];
    // Show private repo
    let isPrivate = false;
    if(req.is_authenticated){
        if(req.user.username !== username) isPrivate = true;
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
            isPrivate: isPrivate,
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
    res.render("repo", ...repo);
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
    console.log(__basedir)
    fs.readFile(__basedir+"/"+content.imagePath, function (err, data) {
        if (err) {
          throw err;
        }
        res.setHeader("content-disposition", "attachment; filename="+content.imageName);
        res.send(data)
      });

})

module.exports = router;