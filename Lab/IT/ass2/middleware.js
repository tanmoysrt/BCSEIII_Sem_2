const prisma = require("./db").getInstance();

class Middleware {
    /**
     * @param {Request} req 
     * @param {Response} res 
     * @param {NextFunction} next 
     */
    static async auth(req, res, next) {
        try {
            const token = req.headers.authorization;
            if(token === null || token === undefined) throw new Error("Unauthorized");
            // Handle login logic
            next();
        } catch (err) {
            // redirect logic
        }
    }
}

module.exports = Middleware;