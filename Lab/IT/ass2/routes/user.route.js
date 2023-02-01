const router = require("express").Router();
const prisma = require("../db").getInstance();
const {AuthMiddleware} = require("../middleware");

// Send list of repo available in profile
router.get("/:username", async (req, res) => {
    const username = req.params["username"];
    // Show private repo
    let showPrivateRepo = false;
    if(req.is_authenticated){
        if(req.user.username === username) showPrivateRepo = true;
    }
    // Fetch all repo list
    let repoList = await prisma.repository.findMany({
        where: {
            user: {
                username: username
            },
            isPrivate: showPrivateRepo ? undefined : false
        },
        select: {
            id: true,
            name: true,
            isPrivate: true,
        }
    })
    // Re-query and get count of contents
    repoList = await Promise.all(repoList.map(async (repo) => {
        const contentCount = await prisma.content.count({
            where: {
                repository: {
                    id: repo.id
                }
            }
        })
        return {
            ...repo,
            contentCount
        }
    }))
    let showForm = false;
    if(req.is_authenticated){
        if(req.user.username == username) showForm = true;
    }
    res.render("repo-list", {
        "username": username,
        "repos": repoList,
        "showForm": showForm
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