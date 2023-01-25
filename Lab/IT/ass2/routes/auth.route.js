const router = require("express").Router();
const prisma = require("../db").getInstance();

// login page
router.get("/login", (req, res) => {
    res.render("login");
})

// Handle login submission
router.post("/login", async (req, res) => {

})

// Handle logout
router.get("/logout", (req, res) => {

})


// Registration page
router.get("/register", (req, res) => {
    res.render("register", {
        "isSuccess": false,
        "isError": false,
    });
})

// Handle registration

router.post("/register", async (req, res) => {
    const name = req.body.username;
    const username =  req.body.username;
    const password = req.body.password;
    if(!username || !password || !name) {
        return res.render("register", {
            "isSuccess": false,
            "isError": true,
            "error": "Please fill in all fields"
        });
    }
    const user = await prisma.user.findFirst({
        where: {
            username: username
        },
        select: {
            id: true,
        }
    })
    if(user != null){
        return res.render("register", {
            "isSuccess": false,
            "isError": true,
            "error": "Username already exists"
        });
    }
    const newUser = await prisma.user.create({
        data: {
            name: name,
            username: username,
            hashedPassword: password
        }
    })
    // TODO login and redirect to home
    res.render("register", {
        "isSuccess": true,
        "isError": false,
        "success": "Registration successful"
    });
})


module.exports = router;