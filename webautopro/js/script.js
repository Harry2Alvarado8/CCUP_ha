
function onclick_b_iniciarSession() {
    in_user = document.getElementById("in_userEmail").value;
    in_contra = document.getElementById("in_pass").value;
    if (in_user == "HARRY" && in_contra == "HARRY"){
        alert("Acceso Seguro")

    } else {
        alert("ACCESO DENEGADO "+in_user+" Contra::: "+in_contra);
    }

}

var oracledb = require('oracledb');


function onclick_b_registrarme() {
    oracledb.getConnection(
        {
            user          : "T66_AUTOPRO",
            password      : "123456",
            connectString : "192.168.0.12/xe"
        },
        function(err, connection)
        {
            if (err) { console.error(err); return; }
            connection.execute( "SELECT * FROM NCLIENTE",
                /*"SELECT department_id, department_name "
                + "FROM departments "
                + "WHERE department_id < 70 "
                + "ORDER BY department_id",*/
                function(err, result)
                {
                    if (err) { console.error(err); return; }
                    console.log(result.rows);
                });
        });
}