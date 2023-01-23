const router = require("express").Router();
const prisma = require("../db").getInstance();

// login page
router.get("/login", (req, res) => {

})

// Handle login submission
router.post("/login", async (req, res) => {

})

// Handle logout
router.get("/logout", (req, res) => {

})


// Registration page
router.get("/register", (req, res) => {

})

// Handle registration
router.post("/register", async (req, res) => {

})


module.exports = router;