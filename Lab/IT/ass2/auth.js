const JWT = require("./utils/jwt");
const prisma = require("./db").getInstance();

const {Request, Response, NextFunction} = require("express");

class AuthMiddleware {
    /**
     * @param {Request} req 
     * @param {Response} res 
     * @param {NextFunction} next 
     */
    static authRequired(req, res, next) {
        if (req.is_authenticated == false) {
            res.redirect("/auth/login");
        }else{
            next();
        }
    }

    /**
     * @param {Request} req 
     * @param {Response} res 
     * @param {NextFunction} next 
     */
    static async resolveUser(req, res, next) {
        if (!req.headers.authorization || JWT.verify(req.headers.authorization) == false) {
            req.is_authenticated = false;
            req.user = undefined;
        }else{
            req.is_authenticated = true;
            const [_, content] = JWT.getContent(req.headers.authorization);
            req.user = await prisma.user.findUnique({
                where: {
                    username: content.username
                },
                select: {
                    id: true,
                    name: true,
                    username: true
                }
            })
        }
    }
}

module.exports = AuthMiddleware;