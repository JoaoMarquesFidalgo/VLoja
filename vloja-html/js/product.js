$(document).ready(function () {
    // Variables
    let products;
    let product;
    let whishlists;
    let filteredWhishlists;

    // Visual
    $(".success-product").hide();
    $(".error-product").hide();
    const userInfo = JSON.parse(localStorage.getItem("user"));

    // Get products from Db
    getProducts();

    function getProducts() {
        // API Call to get products
        $.ajax({
            url: "http://localhost:8080/api/product",
            type: "GET",
            contentType: "application/json",
            success: function (result) {
                products = result["data"];
                populateFrontend(result["data"]);
            }, error: function (data) {
                console.log("Error: " + data.toString());
            }
        });
    }

    function populateFrontend(data) {
        let htmlToAppend = "";
        data.forEach(product => {
            htmlToAppend += `
                <div class="product">
                    <div class="product-img">
                        <img src="http://localhost:8080/images/${product["image"]}" alt="" ">
                    </div>
                    <div class="product-body">
                        <p class="product-category">${product["category"]}</p>
                        <h3 class="product-name"><a href="#">${product["name"]} | ${product["brand"]}</a></h3>
                        <h4 class="product-price">${product["price"]} €</h4>
                        <div class="product-btns">
                            <button class="add-to-whishlist" data-toggle="modal" 
                            data-target="#addToWhislist" data-product-id="${product["id"]}">
                                <i class="fa fa-heart-o"></i><span class="tooltipp">add to whishlist</span>
                            </button>
                            <button class="edit-product" data-toggle="modal" 
                            data-target="#editProductModal" data-product-id="${product["id"]}">
                                <i class="fa fa-pencil"></i><span class="tooltipp">edit product</span>
                            </button>
                            <button class="delete-product" data-toggle="modal" 
                            data-target="#deleteProductModal" data-product-id="${product["id"]}">
                                <i class="fa fa-times"></i><span class="tooltipp">delete product</span>
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

    // Add Product
    $(".click-to-add-product").click(() => addProduct());
    function addProduct() {
        // Validates if inputs sent are correct
        if (validateProduct("add")) {
            closeModalAndSetDescription(true, "Please fill the fields");
        } else {
            $(".error-product").hide();
            // Gets userId from localStorage
            const addProductWithUser = userInfo;
            const objectToSendProduct = {
                "name": $("#nameAddProductForm").val(),
                "price": Number($("#priceAddProductForm").val()),
                "category": $("#categoryAddProduct").val(),
                "brand": $("#brandAddProduct").val(),
                "userId": addProductWithUser.userId
            };
            sendPhotoToServer(objectToSendProduct);
        }
    }

    function validateProduct(addOrEdit) {
        if (addOrEdit == "add") {
            if ($("#nameAddProductForm").val() == "" || $("#priceAddProductForm").val() == ""
                || $("#categoryAddProduct").val() == undefined || $("#brandAddProduct").val() == undefined
                || $("#brandAddProduct").val() == "false" || $('#imageAddProductForm')[0].files[0] == undefined) {
                return true;
            }
            return false;
        } else if (addOrEdit == "edit") {
            if ($("#nameEditProductForm").val() == "" || $("#priceEditProductForm").val() == ""
                || $("#categoryEditProduct").val() == undefined || $("#brandEditProduct").val() == undefined
                || $("#brandEditProduct").val() == "false" || $('#imageEditProductForm')[0].files[0] == undefined) {
                return true;
            }
            return false;
        }
    }

    function sendPhotoToServer(objectToSendProduct, edit = null) {
        const fileToSend = (!edit) ? $('#imageAddProductForm')[0].files[0] : $('#imageEditProductForm')[0].files[0];
        // Creates a formData file in order to send a file to the server
        const photoToSendToServer = new FormData();
        photoToSendToServer.append("file", fileToSend);
        sendPhotoAndAddOrEditProduct(objectToSendProduct, photoToSendToServer, edit);
    }

    function sendPhotoAndAddOrEditProduct(objectToSendProduct, photoToSendToServer, addOrEdit) {
        // Makes an API request to upload the photo to the server
        $.ajax({
            url: "http://localhost:8080/api/productImage",
            type: "POST",
            contentType: "application/json",
            data: photoToSendToServer,
            contentType: false,
            processData: false,
            success: function (result) {
                // Gets the name of the file inserted and adds to the product object,
                // then calls the function to add the product 
                objectToSendProduct["image"] = result["data"];
                (!addOrEdit) ? addProductToServer(objectToSendProduct) : editProductToServer(objectToSendProduct);
            }, error: function (data) {
                // If there is an error inserting the file, it will not add to server,
                // instead, it will set a description error 
                console.log("Error: " + data.toString());
                closeModalAndSetDescription(true, "There was an error upload the file to the server, please enter a valid image");
            }
        });
    }

    function addProductToServer(objectToSendProduct) {
        $.ajax({
            url: "http://localhost:8080/api/product",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(objectToSendProduct),
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
            $(".success-product").hide();
            $(".error-product").show();
            $(".error-product").text(description);
        }
        // Comes from an error in the server processing, eg. not a valid userId
        else if (error && result["description"]) {
            $(".success-product").hide();
            $(".error-product").show();
            $(".error-product").text(result["description"]);
        }
        // Comes from successfull server processing
        else if (!error && result) {
            if (edit) {
                $("#editProductModal").modal("hide");
            } else if (remove) {
                $("#deleteProductModal").modal("hide");
            } else {
                $("#addProductModal").modal("hide");
            }
            $(".error-product").hide();
            $(".success-product").show();
            $(".success-product").text(result["description"] +
                ", You will be redirected in 4 seconds");
            setTimeout(() => location.assign("./index.html"), 4000);
        }
    }

    // Called when press button to edit a product
    $('#editProductModal').on('show.bs.modal', function (e) {
        // Get data-id attribute of the clicked element
        const productId = $(e.relatedTarget).data('product-id');
        // Checks if user is logged
        const user = userInfo;
        if (user) {
            const userId = user.userId;
            // Checks if the user is the creator of the product, in order to edit
            product = products.find((product) => (product['userId'] == userId) && (product['id'] == productId));
            if (!product) {
                showEditProductErrorMessage("No rights to edit this product", e);
            } else {
                fillEditModal(product);
            }
        } else {
            showEditProductErrorMessage("Login to edit products");
        }
    });

    function fillEditModal(product) {
        $("#nameEditProductForm").val(product["name"]);
        $("#priceEditProductForm").val(product["price"]);
        $("#nameEditProductForm").val(product["name"]);
        populateCategory(product);
    }

    function showEditProductErrorMessage(message, e) {
        $(".error-product").show().html(message);
        e.preventDefault();
        setTimeout(() => $(".error-product").hide(), 4000);
    }

    function populateCategory(product) {
        $.ajax({
            url: "http://localhost:8080/api/category-brand",
            success: function (result) {
                // Receives data and parses the string received to JSON
                dataInJsonFormat = JSON.parse(result["data"]);
            }, complete: function (data) {
                // After the requests is finished, appends the category entries to the dropdown
                dataInJsonFormat.forEach((element) => {
                    $("#categoryEditProduct").append(
                        `<option value="${element["category_name"]}">
                            ${element["category_name"]}
                        </option>`);
                });
                $("#categoryEditProduct").val(product["category"]);
                populateBrand(product);
            }
        });
    }

    function populateBrand(product) {
        const category = dataInJsonFormat.find((categories) => categories["category_name"] === product["category"]);
        const brandDropdown = $("#brandEditProduct");
        brandDropdown.empty().append(`<option value=false>Brand</option>`);
        brandDropdown.prop('disabled', false);
        // Appends the brands to the dropdownlist according to category
        category["brands"].forEach((element) => {
            if (element["brand_name"] == product["brand"]) {
                brandDropdown.append(
                    `<option value="${element["brand_name"]}" selected>
                        ${element["brand_name"]}
                    </option>`);
            } else {
                brandDropdown.append(
                    `<option value="${element["brand_name"]}">
                        ${element["brand_name"]}
                    </option>`);
            }
        });
    }

    $(".click-to-edit-product").click(() => editProduct());
    function editProduct() {
        // Validates if inputs sent are correct
        if (validateProduct("edit")) {
            closeModalAndSetDescription(true, "Please fill the fields");
        } else {
            $(".error-product").hide();
            // Gets userId from localStorage
            const editProductWithUser = userInfo;
            const objectToSendProduct = {
                "name": $("#nameEditProductForm").val(),
                "price": Number($("#priceEditProductForm").val()),
                "category": $("#categoryEditProduct").val(),
                "brand": $("#brandEditProduct").val(),
                "userId": editProductWithUser.userId
            };
            sendPhotoToServer(objectToSendProduct, "edit");
        }
    }

    function editProductToServer(objectToSendProduct) {
        const editProductWithUser = userInfo;
        const url = "http://localhost:8080/api/product/" + product["id"] + "/" + editProductWithUser.userId;
        $.ajax({
            url: url,
            type: "PUT",
            contentType: "application/json",
            data: JSON.stringify(objectToSendProduct),
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

    // Delete product
    // Called when press button to delete a product
    $('#deleteProductModal').on('show.bs.modal', function (e) {
        // Get data-id attribute of the clicked element
        const productId = $(e.relatedTarget).data('product-id');
        // Checks if user is logged
        const user = userInfo;
        if (user) {
            const userId = user.userId;
            // Checks if the user is the creator of the product, in order to delete
            product = products.find((product) => (product['userId'] == userId) && (product['id'] == productId));
            if (!product) {
                showEditProductErrorMessage("No rights to delete this product", e);
            }
        } else {
            showEditProductErrorMessage("Login to delete products");
        }
    });

    // Delete product
    $(".click-to-delete-product").click(() => deleteProduct());
    function deleteProduct() {
        const deleteProductWithUser = userInfo;
        const url = "http://localhost:8080/api/product/" + product["id"] + "/" + deleteProductWithUser.userId;
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

    // Whishlist
    // Called when press button to edit a product
    $('#addToWhislist').on('show.bs.modal', function (e) {
        // Get data-id attribute of the clicked element
        const productId = $(e.relatedTarget).data('product-id');
        // Checks if user is logged
        const user = userInfo;
        getWhishlists(productId, user);
    });

    function getWhishlists(productId, user) {
        $.ajax({
            url: "http://localhost:8080/api/list",
            type: "GET",
            contentType: "application/json",
            success: function (result) {
                whishlists = result["data"];
                fillWhishlistModal(productId, user);
            }, error: function (data) {
                console.log("Error: " + data.toString());
            }
        });
    }
    function fillWhishlistModal(productId, user) {
        filteredWhishlists = whishlists.filter((whishlist) => whishlist["userId"] == user["userId"]);
        product = products.find((product) => product['id'] == productId);
        $("#fill-whishlist").empty();
        $("#fill-whishlist").append(`<p>Add the product ${product["name"]} to your whishlists</p>
            <p></p>
            <ul class="list-group">
        `);
        filteredWhishlists.forEach((whishlist) => {
            $("#fill-whishlist").append(`
                <li class="list-group-item">
                    ${whishlist["alias"]} <input class="whishlist-checkbox" type="checkbox" value="${whishlist["id"]}" style="width: 15px; height: 15px;">
                </li>
            `)
        });
        $("#fill-whishlist").append(`</ul>`);
    }
    $(".click-to-add-to-whishlist").click(() => addToWhishlist());
    function addToWhishlist() {
        // retrieves the id's from the checkbox values
        let whishlistValues = [];
        $(':checkbox:checked').each(function (i) {
            whishlistValues[i] = $(this).val();
        });
        let stateOfInsert = [];
        for(let i = 0; i < whishlistValues.length; i++) {
            insertProductToWhishlist(whishlistValues[i], stateOfInsert);
        }
        $("#addToWhislist").modal("hide");
        if (stateOfInsert.includes(true)) {
            $(".error-product").show().html("Product already exists in the list");
            $(".success-product").hide();
        } else {
            $(".error-product").hide();
            $(".success-product").show();
            $(".success-product").text("Product was added to the lists, you will be redirected in 4 seconds");
            setTimeout(() => location.assign("./index.html"), 4000);
        }
    }

    function insertProductToWhishlist(whishlistId, stateOfInsert) {
        const productToWhishlist = {
            "product": product["id"],
            "list": whishlistId,
            "wasBought": false
        }
        $.ajax({
            url: "http://localhost:8080/api/productList",
            type: "POST",
            contentType: "application/json",
            async: false,
            data: JSON.stringify(productToWhishlist),
            success: function (result) {
                stateOfInsert.push(result["error"]);
            }, error: function (data) {
                console.log("Error: " + data.toString());
            }
        });
    }
});