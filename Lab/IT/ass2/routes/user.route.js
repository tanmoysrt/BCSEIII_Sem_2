const router = require("express").Router();
const prisma = require("../db").getInstance();
const {AuthMiddleware} = require("../middleware");

// Send list of repo available in profile
router.get("/:username", async (req, res) => {
    const username = req.params["username"];
    // Show private repo
    let isPrivate = false;
    if(req.is_authenticated){
        if(req.user.username === username) isPrivate = true;
    }
    // Fetch all repo list
    const repoList = await prisma.repository.findMany({
        where: {
            isPrivate: isPrivate,
            user: {
                username: username
            }
        },
        select: {
            id: true,
            name: true
        }
    })
    res.render("repo-list", {
        "username": username,
        "repos": repoList
    });
})

// Register new repo
router.post("/:username", AuthMiddleware.loginRequired, async (req, res) => {
    /** @type {string} */
    let repoName = req.body["repo-name"];
    repoName = repoName.replace(" ", "_")
    // Check duplicate repo name
    const repo = await prisma.repository.findFirst({
        where: {
            name: repoName,
            user: {
                username: req.user.username
            }
        }
    })
    if (repo != null) {
        repoName = repoName+"_"+Math.floor(Math.random() * 1000).toString();
    }
    // Create new repo
    await prisma.repository.create({
        data: {
            name: repoName,
            isPrivate: false,
            user: {
                connect: {
                    id: req.user.id
                }
            }
        }
    })
    res.redirect("/");
})

module.exports = router;