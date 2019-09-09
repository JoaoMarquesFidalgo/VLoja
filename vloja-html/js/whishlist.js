$(document).ready(function () {
    // Visual
    $(".success-whishlist").hide();
    $(".error-whishlist").hide();
    const userInfo = JSON.parse(localStorage.getItem("user"));
    // Get whishlists from Db
    getWhishlists();
    let whishlists;
    let whishlist;
    let users;

    $("#datepickerAddWhishlistForm").datepicker();
    $("#datepickerEditWhishlistForm").datepicker();

    function getWhishlists() {
        $.ajax({
            url: "http://localhost:8080/api/user",
            type: "GET",
            contentType: "application/json",
            success: function (result) {
                users = result["data"];
            }, error: function (data) {
                console.log("Error: " + data.toString());
            },
            complete: function (result) { 
                // API Call to get whishlists
                $.ajax({
                    url: "http://localhost:8080/api/list",
                    type: "GET",
                    contentType: "application/json",
                    success: function (result) {
                        whishlists = result["data"];
                        populateFrontend(result["data"]);
                    }, error: function (data) {
                        console.log("Error: " + data.toString());
                    }
                });
            }
        });
    }

    function populateFrontend(data) {
        let htmlToAppend = "";
        data.forEach(whishlist => {
            const userDetails = users.find((user) => user["userId"] == whishlist["userId"]);
            htmlToAppend += `
            <div class="product">
                <div class="product-img">
                    <img src="http://localhost:8080/images/list.jpg" alt="" ">
                </div>
                <div class="product-body">
                    <p class="product-category">${userDetails["name"]}</p>
                    <p class="product-category">${whishlist["category"]}</p>
                    <p class="product-category">${whishlist["date"]}</p>
                    <h3 class="product-name"><a style="cursor: pointer;">
                        <span data-toggle="modal" data-target="#productsWhishlistModal" data-whishlist-id="${whishlist["id"]}">
                            ${whishlist["alias"]}</span></a></h3>
                    <div class="product-btns">
                        <button class="edit-product" data-toggle="modal" 
                        data-target="#editWhishlistModal" data-whishlist-id="${whishlist["id"]}">
                            <i class="fa fa-pencil"></i><span class="tooltipp">edit whishlist</span>
                        </button>
                        <button class="delete-product" data-toggle="modal" 
                        data-target="#deleteWhishlistModal" data-whishlist-id="${whishlist["id"]}">
                            <i class="fa fa-times"></i><span class="tooltipp">delete whishlist</span>
                        </button>
                    </div>
                </div>
            </div>`
        });
        $(".products-slick").append(htmlToAppend);
        $('.products-slick').slick("unslick");
        sliderInit();
    }

    function sliderInit() {
        $('.products-slick').slick({
            slidesToShow: 6,
            slidesToScroll: 1,
            autoplay: true,
            infinite: true,
            speed: 300,
            dots: false,
            arrows: true,
            responsive: [
                {
                    breakpoint: 900,
                    settings: {
                        slidesToShow: 2,
                        slidesToScroll: 1,
                    }
                },
                {
                    breakpoint: 600,
                    settings: {
                        slidesToShow: 1,
                        slidesToScroll: 1,
                    }
                },
            ]
        });
    };

    // Add Whishlist
    $(".click-to-add-whishlist").click(() => addWhishlist());
    function addWhishlist() {
        // Validates if inputs sent are correct
        if (validateWhishlist("add")) {
            closeModalAndSetDescription(true, "Please fill the fields");
        } else {
            $(".error-whishlist").hide();
            // Gets userId from localStorage
            const addWhishlistWithUser = userInfo;
            const objectToSendWhishlist = {
                "alias": $("#aliasAddWhishlistForm").val(),
                "category": $("#categoryAddWhishlistForm").val(),
                "date": $("#datepickerAddWhishlistForm").val(),
                "userId": addWhishlistWithUser.userId
            };
            addWhishlistToServer(objectToSendWhishlist);
        }
    }

    function validateWhishlist(addOrEdit) {
        if (addOrEdit == "add") {
            if ($("#aliasAddWhishlistForm").val() == "" || $("#categoryAddWhishlistForm").val() == ""
                || $("#datepickerAddWhishlistForm").val() == "") {
                return true;
            }
            return false;
        } else if (addOrEdit == "edit") {
            if ($("#aliasEditWhishlistForm").val() == "" || $("#categoryEditWhishlistForm").val() == ""
                || $("#datepickerEditWhishlistForm").val() == "") {
                return true;
            }
            return false;
        }
    }

    function addWhishlistToServer(objectToSendWhishlist) {
        $.ajax({
            url: "http://localhost:8080/api/list",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(objectToSendWhishlist),
            success: function (result) {
                // If the error is true, something wrong happen with the processing of the request, 
                // eg. wrong credentials, and gives the user an error message
                // If no error, assigns localStorage with data received and gives feedback. 
                // It sets a timeout to go to the next page
                if (result["error"]) {
                    closeModalAndSetDescription(true, null, result);
                } else {
                    closeModalAndSetDescription(false, null, result);
                }
            }, error: function (data) {
                console.log("Error: " + data.toString());
            }
        });
    }

    // Called after sending photo to server, incase there is an error
    function closeModalAndSetDescription(error, description = null, result = null, edit = null, remove = null) {
        // Comes from a photo error
        if (error && !result) {
            $(".success-whishlist").hide();
            $(".error-whishlist").show();
            $(".error-whishlist").text(description);
        }
        // Comes from an error in the server processing, eg. not a valid userId
        else if (error && result["description"]) {
            $(".success-whishlist").hide();
            $(".error-whishlist").show();
            $(".error-whishlist").text(result["description"]);
        }
        // Comes from successfull server processing
        else if (!error && result) {
            if (edit) {
                $("#editWhishlistModal").modal("hide");
            } else if (remove) {
                $("#deleteWhishlistModal").modal("hide");
            } else {
                $("#addWhishlistModal").modal("hide");
            }
            $(".error-whishlist").hide();
            $(".success-whishlist").show();
            $(".success-whishlist").text(result["description"] +
                ", You will be redirected in 4 seconds");
            setTimeout(() => location.assign("./whishlist.html"), 4000);
        }
    }

    // Called when press button to edit a whishlist
    $('#editWhishlistModal').on('show.bs.modal', function (e) {
        // Get data-id attribute of the clicked element
        const whishlistId = $(e.relatedTarget).data('whishlist-id');
        // Checks if user is logged
        const user = userInfo;
        if (user) {
            const userId = user.userId;
            // Checks if the user is the creator of the whishlist, in order to edit
            whishlist = whishlists.find((whishlist) => (whishlist['userId'] == userId) && (whishlist['id'] == whishlistId));
            if (!whishlist) {
                showEditProductErrorMessage("No rights to edit this whishlist", e);
            } else {
                fillEditModal(whishlist);
            }
        } else {
            showEditProductErrorMessage("Login to edit whishlists");
        }
    });

    function fillEditModal(whishlist) {
        $("#aliasEditWhishlistForm").val(whishlist["alias"]);
        $("#categoryEditWhishlistForm").val(whishlist["category"]);
        $("#datepickerEditWhishlistForm").val(whishlist["date"]);
    }

    function showEditProductErrorMessage(message, e) {
        $(".error-whishlist").show().html(message);
        e.preventDefault();
        setTimeout(() => $(".error-whishlist").hide(), 4000);
    }

    $(".click-to-edit-whishlist").click(() => editWhishlist());
    function editWhishlist() {
        // Validates if inputs sent are correct
        if (validateWhishlist("edit")) {
            closeModalAndSetDescription(true, "Please fill the fields");
        } else {
            $(".error-whishlist").hide();
            // Gets userId from localStorage
            const editWhishlistWithUser = userInfo;
            const objectToSendWhishlist = {
                "alias": $("#aliasEditWhishlistForm").val(),
                "category": $("#categoryEditWhishlistForm").val(),
                "date": $("#datepickerEditWhishlistForm").val(),
                "userId": editWhishlistWithUser.userId
            };
            editWhishlistToServer(objectToSendWhishlist)
        }
    }

    function editWhishlistToServer(objectToSendWhishlist) {
        const editWhishlistWithUser = userInfo;
        const url = "http://localhost:8080/api/list/" + whishlist["id"] + "/" + editWhishlistWithUser.userId;
        $.ajax({
            url: url,
            type: "PUT",
            contentType: "application/json",
            data: JSON.stringify(objectToSendWhishlist),
            success: function (result) {
                // If the error is true, something wrong happen with the processing of the request, 
                // eg. wrong credentials, and gives the user an error message
                // If no error, assigns localStorage with data received and gives feedback. 
                // It sets a timeout to go to the next page
                if (result["error"]) {
                    closeModalAndSetDescription(true, null, result);
                } else {
                    closeModalAndSetDescription(false, null, result, true);
                }
            }, error: function (data) {
                console.log("Error: " + data.toString());
            }
        });
    }

    // Delete whishlist
    // Called when press button to delete a whishlist
    $('#deleteWhishlistModal').on('show.bs.modal', function (e) {
        // Get data-id attribute of the clicked element
        const whishlistId = $(e.relatedTarget).data('whishlist-id');
        // Checks if user is logged
        const user = userInfo;
        if (user) {
            const userId = user.userId;
            // Checks if the user is the creator of the whishlist, in order to delete
            whishlist = whishlists.find((whishlist) => (whishlist['userId'] == userId) && (whishlist['id'] == whishlistId));
            if (!whishlist) {
                showEditProductErrorMessage("No rights to delete this whishlist", e);
            }
        } else {
            showEditProductErrorMessage("Login to delete whishlists");
        }
    });

    // Delete whishlist
    $(".click-to-delete-whishlist").click(() => deleteWhishlist());
    function deleteWhishlist() {
        const deleteWhishlistWithUser = userInfo;
        const url = "http://localhost:8080/api/list/" + whishlist["id"] + "/" + deleteWhishlistWithUser.userId;
        $.ajax({
            url: url,
            type: "DELETE",
            contentType: "application/json",
            success: function (result) {
                // If the error is true, something wrong happen with the processing of the request, 
                // eg. wrong credentials, and gives the user an error message
                // If no error, assigns localStorage with data received and gives feedback. 
                // It sets a timeout to go to the next page
                if (result["error"]) {
                    closeModalAndSetDescription(true, null, result);
                } else {
                    closeModalAndSetDescription(false, null, result, null, true);
                }
            }, error: function (data) {
                console.log("Error: " + data.toString());
            }
        });
    }

    // Show whishlist products
    $('#productsWhishlistModal').on('show.bs.modal', function (e) {
        // Get data-id attribute of the clicked element
        const whishlistId = $(e.relatedTarget).data('whishlist-id');
        whishlist = whishlists.find((whishlist) => whishlist['id'] == whishlistId);
        // API call to get products to show
        $.ajax({
            url: "http://localhost:8080/api/product",
            type: "GET",
            contentType: "application/json",
            success: function (result) {
                // If the error is true, something wrong happen with the processing of the request, 
                // eg. wrong credentials, and gives the user an error message
                // If no error, assigns localStorage with data received and gives feedback. 
                // It sets a timeout to go to the next page
                if (result["error"]) {
                    closeModalAndSetDescription(true, null, result);
                } else {
                    setVisualOfWhishlistProducts(result["data"], whishlist);
                }
            }, error: function (data) {
                console.log("Error: " + data.toString());
            }
        });
    });

    function setVisualOfWhishlistProducts(products, mainWhishlist) {
        const listOfProductOfWhishlist = mainWhishlist["productList"].map((productList) => {
            return products.find((product) => productList["product"] == product["id"]);
        });
        if (listOfProductOfWhishlist.length == 0)
        {
            $("#fill-products").empty();    
            $("#fill-products").append(`<p>The list does not contain any product yet</p>`);
        } else {
            $("#fill-products").empty();
            $("#fill-products").append(`<p>A list of products of the whislist ${mainWhishlist["alias"]}</p>
                <p></p>
                <ul class="list-group">
            `);
            let i = 0;
            listOfProductOfWhishlist.forEach((product) => {
                const wasBought = (mainWhishlist["productList"][i]["wasBought"])? "checked": null;
                $("#fill-products").append(`
                    <li class="list-group-item">
                        <h5>${product["name"]}</h5> 
                        <p><span>
                            <input class="whishlist-checkbox" type="checkbox" style="width: 15px; height: 15px;" ${wasBought}> Was Bought?
                        </span></p>
                    </li>
                `)
                i++;
            });
            $("#fill-products").append(`</ul>`);
        }
    }
    // Change was bought value of products of list
    $(".click-to-change-products-whishlist").click(() => deleteWhishlist());
    function deleteWhishlist() {
        let wasBoughtValues = [];
        $(':checkbox').each(function (i) {
            wasBoughtValues[i] = $(this).is(":checked");
        });
        let objectToUpdate = []; 
        let i = 0;
        whishlist["productList"].forEach((productList) => {
            objectToUpdate.push({
                "product": productList["product"],
                "list": productList["list"],
                "wasBought": wasBoughtValues[i]? 1: 0
            });
            i++;
        });
        let stateOfUpdate = [];
        i = 0;
        objectToUpdate.forEach((object) => {
            updateProductList(whishlist["productList"][i]["productListId"], object["list"], 
                userInfo.userId, object, stateOfUpdate);
            i++;
        });
        $("#productsWhishlistModal").modal("hide");
        if (stateOfUpdate.some((state) => state["error"] == true)) {
            $(".error-whishlist").show().html(stateOfUpdate.find((state) => state["error"] == true)['description']);
            $(".success-whishlist").hide();
        } else {
            $(".error-whishlist").hide();
            $(".success-whishlist").show();
            $(".success-whishlist").text("Whishlist was updated, you will be redirected in 4 seconds");
            setTimeout(() => location.assign("./whishlist.html"), 4000);
        }
    }

    function updateProductList(productListId, listId, userId, objectToSendProduct, stateOfUpdate) {
        // /productList/{productListId}/{listId}/{userId}
        const url = "http://localhost:8080/api/productList/" + productListId + "/" 
            + listId + "/" + userId;
        $.ajax({
            url: url,
            type: "PUT",
            contentType: "application/json",
            async: false,
            data: JSON.stringify(objectToSendProduct),
            success: function (result) {
                console.log(result);
                stateOfUpdate.push({
                    error: result["error"],
                    description: result["description"]
                });
            }, error: function (data) {
                console.log("Error: " + data.toString());
            }
        });
    }
});