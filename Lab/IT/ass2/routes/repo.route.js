const router = require("express").Router();
const prisma = require("../db").getInstance();

// Show repo details
router.get("/", (req, res) => {
    res.render("repo");
})

// Handle new content submission (POST)
router.post("/", async (req, res) => {

});

// Update visibility of repo
router.patch("/", async (req, res) => {

})

module.exports = router;