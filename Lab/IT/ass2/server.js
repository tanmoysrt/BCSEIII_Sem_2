require('dotenv').config();

const PORT = parseInt(process.env.PORT);
const DEBUG = parseInt(process.env.DEBUG);

// Express
const express = require('express');
const app = express();

// Config
app.disable('x-powered-by')
app.set('view engine', 'ejs');
app.use(express.urlencoded({ extended: true }));

// Route
app.use("", require("./routes/home.route"));
app.use("/auth", require("./routes/auth.route"));
app.use("/:username", require("./routes/user.route"));
app.use("/:username/:reponame", require("./routes/repo.route"));


// Global error handler
app.use((err, req, res, next) => {
    console.error(err);
    res.status(500).json({ error: "Unexpected Error" });
})

app.listen(PORT, () => console.log(`🚀 @ http://localhost:${PORT}`));