$(document).ready(function () {
    // Checks if user is not logged and redirects to login, otherwise, sets input with user info
    if (!localStorage.getItem("user")) {
        location.assign("./login.html");
    } 
    // TODO:: set localStorage by login, then give field text
    else {
        setInitialValues();
    }

    // Initial visual setup
    visualChanges(false); 

    // Clicking action
    $(".click-to-edit-account").click(() => visualChanges(true));
    $(".click-to-cancel-account").click(() => visualChanges(false, true));

    // Change buttons visibility 
    function visualChanges(showOrHideEditButtons, resetForm = null) {
        (resetForm) ? setInitialValues(): null;
        if (!showOrHideEditButtons) {
            $(".error-account").hide();
            $(".success-account").hide();
            $("#edit-buttons").show();
            $("#save-cancel-buttons").hide();
            $("#nameRegisterForm").prop('disabled', true);
            $("#languageEnRegisterForm").prop('disabled', true);
            $("#languagePtRegisterForm").prop('disabled', true);
            $("#emailRegisterForm").prop('disabled', true);
            $("#passwordRegisterForm").prop('disabled', true);
            $("#imageRegisterForm").prop('disabled', true);
        } else {
            $("#edit-buttons").hide();
            $("#save-cancel-buttons").show();
            $("#nameRegisterForm").prop('disabled', false);
            $("#languageEnRegisterForm").prop('disabled', false);
            $("#languagePtRegisterForm").prop('disabled', false);
            $("#emailRegisterForm").prop('disabled', false);
            $("#passwordRegisterForm").prop('disabled', false);
            $("#imageRegisterForm").prop('disabled', false);
        }
    }

    // Set input fields values according to localStorage content
    function setInitialValues() {
        const user = JSON.parse(localStorage.getItem("user"));
        $("#nameRegisterForm").val(user.name);
        $("#emailRegisterForm").val(user.email);
        $("#passwordRegisterForm").val(user.password);
        (user.language == "PT") ? $("#languagePtRegisterForm").prop("checked", true): $("#languageEnRegisterForm").prop("checked", true);
    }

    // Update Account
    $(".click-to-save-account").click(() => updateUser());
    // Makes an API call to update the user
    function updateUser() {
        const objectToSave = {
            "email": $("#emailRegisterForm").val(),
            "password": $("#passwordRegisterForm").val(),
            "name": $("#nameRegisterForm").val(),
            "language": ($("#languagePtRegisterForm").is(':checked'))? 'PT': 'EN',
            "image": ""
        };
        const userToUpdate = JSON.parse(localStorage.getItem("user"));
        // Makes the API request, transforming the object to a string and setting content-type to JSON
        $.ajax({
            url: "http://localhost:8080/api/user/" + parseInt(userToUpdate.userId),
            type: 'PUT',
            contentType: "application/json",
            data: JSON.stringify(objectToSave),
            success: function (result) {
                // If the error is true, something wrong happen with the processing of the request, 
                // eg. duplicate emails, and gives the user an error message
                // If no error, shows a success message
                if (result["error"]) {
                    $(".success-account").hide();
                    $(".error-account").show();
                    $(".error-account").text(result["description"]);
                } else {
                    visualChanges(false);
                    $(".error-account").hide();
                    $(".success-account").show();
                    $(".success-account").text(result["description"]);
                    localStorage.setItem("user", JSON.stringify(result["data"]));
                }
            }, error: function (data) {
                console.log("Error: " + data);
            }
        });
    }

});