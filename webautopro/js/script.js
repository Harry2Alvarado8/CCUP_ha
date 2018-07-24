
function onclick_b_iniciarSession() {
    in_user = document.getElementById("in_userEmail").value;
    in_contra = document.getElementById("in_pass").value;
    if (in_user == "HARRY" && in_contra == "HARRY"){
        alert("Acceso Seguro")

    } else {
        alert("ACCESO DENEGADO "+in_user+" Contra::: "+in_contra);
    }

}

function onclick_b_registrarme() {
    alert("NICE!!!!! se REGISTRARME  datos en la tabla!!!");
}
