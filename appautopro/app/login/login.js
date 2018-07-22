/*
var observableModule = require("data/observable");

var user = new observableModule.fromObject({
    email: "",
    password: ""
});
exports.loaded = function (args) {
    page = args.object;
    page.bindingContext = user;
};

var correo
var contra

exports.iniciar = function () {
    correo = page.getViewById("correo");
    console.log(correo.text);

    contra = page.getViewById("contra");
    console.log(contra.text);

};
exports.iniciar = function () {
    alert("INICIAR SESSION");
};

exports.registar = function () {
    alert("REGISTRARME");
};
*/

const frameModule = require("ui/frame");
const LoginViewModel = require("./login-view-model");

const loginViewModel = new LoginViewModel();

exports.pageLoaded = function (args) {
    const page = args.object;
    page.bindingContext = loginViewModel;
}




