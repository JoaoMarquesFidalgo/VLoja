$(document).ready(function () {
    // Checks if user is logged and redirects to account
    if (localStorage.getItem("user")) {
        location.assign("./account.html");
    }

    // Visual
    $(".success-account").hide();
    $(".error-account").hide();

    // Login
    $(".click-to-login-account").click(() => login());
    function login() {
        const objectToSend = {
            "email": $("#emailLoginForm").val(),
            "password": $("#passwordlLoginForm").val()
        };
        // Makes the API request, transforming the object to a string and setting content-type to JSON
        $.ajax({
            url: "http://localhost:8080/api/user/login",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(objectToSend),
            success: function (result) {
                // If the error is true, something wrong happen with the processing of the request, 
                // eg. wrong credentials, and gives the user an error message
                // If no error, assigns localStorage with data received and gives feedback. 
                // It sets a timeout to go to the next page
                if (result["error"]) {
                    $(".success-account").hide();
                    $(".error-account").show();
                    $(".error-account").text(result["description"]);
                } else {
                    $(".error-account").hide();
                    $(".success-account").show();
                    $(".success-account").text(result["description"] + 
                        ", You will be redirected in 4 seconds");
                    localStorage.setItem("user", JSON.stringify(result["data"]));
                    setTimeout(() => location.assign("./account.html"), 4000);
                }
            }, error: function (data) {
                console.log("Error: " + data);
            }
        });
    }
});