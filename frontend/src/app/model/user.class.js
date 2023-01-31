"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.User = void 0;
var Photo = /** @class */ (function () {
    function Photo() {
        this.id = 0;
        this.path = '';
    }
    return Photo;
}());
var User = /** @class */ (function () {
    function User(obj) {
        this.id = 0;
        this.name = '';
        this.lastName = '';
        this.email = '';
        this.phone = '';
        this.city = '';
        this.blocked = true;
        this.activated = false;
        this.accessToken = '';
        this.role = '';
        obj && Object.assign(this, JSON.parse(obj.toString()));
    }
    User.prototype.getPhoto = function () {
        var _a;
        return (_a = this.photo) === null || _a === void 0 ? void 0 : _a.path;
    };
    return User;
}());
exports.User = User;
