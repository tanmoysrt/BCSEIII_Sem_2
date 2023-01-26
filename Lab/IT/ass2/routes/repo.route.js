const router = require("express").Router();
const prisma = require("../db").getInstance();

// Show repo details
router.get("/:username/:reponame", (req, res) => {
    res.render("repo");
})

// Handle new content submission (POST)
router.post("/:username/:reponame", async (req, res) => {

});

// Update visibility of repo
router.patch("/:username/:reponame", async (req, res) => {

})

module.exports = router;