const observableModule = require("data/observable");
const dialogsModule = require("ui/dialogs");
//const userService = require("~/services/user-service");
const topmost = require("ui/frame").topmost;

function LoginViewModel() {
    const viewModel = observableModule.fromObject({
        email: "exito@hackrry.com",
        password: "nscore",
        confirmPassword: "",
        isLoggingIn: true,
        toggleForm() {
            this.isLoggingIn = !this.isLoggingIn;
        },
        submit() {
            if (this.email.trim() === "" || this.password.trim() === "") {
                alert("Please provide both an email address and password.");
                return;
            }

            if (this.isLoggingIn) {
                this.login();
            } else {
                this.register();
            }
        },
        login() {
            console.log("Quieres iniciar session");
            //topmost().navigate("./home/home-page");//<---- ahora mismo no funciona. by harry
       /*     userService.login({
                email: this.email,
                password: this.password
            }).then(() => {
                console.log("Estas aqui!!!");
                topmost().navigate("./home/home-page");//<---- ahora mismo no funciona. by harry

            })
                .catch((e) => {
                    alert("Unfortunately we could not find your account.");
                });
        },
        register() {
            if (this.password != this.confirmPassword) {
                alert("Your passwords do not match.");
                return;
            }
            userService.register({
                email: this.email,
                password: this.password
            }).then(() => {
                alert("Your account was successfully created. You can now login.");
                this.isLoggingIn = true;
            })
                .catch(() => {
                    alert("Unfortunately we were unable to create your account.");
                });
        },
        forgotPassword() {
            dialogsModule.prompt({
                title: "Forgot Password",
                message: "Enter the email address you used to register for APP NAME to reset your password.",
                inputType: "email",
                defaultText: "",
                okButtonText: "Ok",
                cancelButtonText: "Cancel"
            }).then((data) => {
                if (data.result) {
                    userService.resetPassword(data.text.trim())
                        .then(() => {
                            alert("Your password was successfully reset. Please check your email for instructions on choosing a new password.");
                        }).catch(() => {
                            alert("Unfortunately, an error occurred resetting your password.");
                        });
                }
                });
            */
        }
    });

    return viewModel;
}

module.exports = LoginViewModel;