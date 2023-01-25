const bcrypt = require("bcrypt")

class Password {
    static async make(password) {
        const salt = await bcrypt.genSalt(10);
        const hash = await bcrypt.hash(password, salt);
        console.log(hash);
    }
    static async check(password, hash) {
        const result = await bcrypt.compare(password, hash);
        return result;
    }
}