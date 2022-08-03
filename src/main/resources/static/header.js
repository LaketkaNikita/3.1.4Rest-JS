fetch("/api/user")
    .then(response => {
            response.json().then(
                data => {
                    let header = "";
                    let rolesName = "";
                    data.roles.forEach(r => {
                        rolesName += r.role + " ";
                    })
                    header = data.email + " with roles " + rolesName;
                    document.getElementById("headerFragment").innerHTML = header;
                }
            )
        }
    )