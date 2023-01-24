const router = require("express").Router();
const prisma = require("../db").getInstance();

// Landing page
router.get("/", (req, res) => {
    res.render("home");
})

module.exports = router;