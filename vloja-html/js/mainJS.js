$(document).ready(function () {
    const categoryDropdown = $("#categoryAddProduct");
    const brandDropdown = $("#brandAddProduct");
    let selectedCategory;
    let dataInJsonFormat;

    // Local Storage implementation
    if (localStorage.getItem("user")) {
        $(".user-not-logged").hide();
        $(".user-logged").show();
    } else {
        $(".user-not-logged").show();
        $(".user-logged").hide();
    }
        
    // Populate category and brands according to API call
    populateCategory();
    function populateCategory () {
        $.ajax({
            url: "http://localhost:8080/api/category-brand",
            success: function (result) {
                // Receives data and parses the string received to JSON
                dataInJsonFormat = JSON.parse(result["data"]);
            }, complete: function (data) {
                // After the requests is finished, appends the category entries to the dropdown
                dataInJsonFormat.forEach((element) => {
                    categoryDropdown.append(
                        `<option value="${element["category_name"]}">
                            ${element["category_name"]}
                        </option>`);
                });
            }
        });
    }
    
    // Fill the brand dropdown when selecting a category
    categoryDropdown.change(() => {
        // Get selected category
        selectedCategory = $("#categoryAddProduct option:selected").val();
        // Find the element received from the API that corresponds to the selected category
        const elementToFillBrand = dataInJsonFormat.find((element) => element["category_name"] == selectedCategory);
        // Visual changes
        brandDropdown.empty().append(`<option value=false>Brand</option>`);
        brandDropdown.prop('disabled', false);
        // Appends the brands to the dropdownlist according to category
        elementToFillBrand["brands"].forEach((element) => {
            brandDropdown.append(
                `<option value="${element["brand_name"]}">
                    ${element["brand_name"]}
                </option>`);
        });
    });

    $(".logout").click(() => {
        localStorage.clear();
        location.reload();
    })
});