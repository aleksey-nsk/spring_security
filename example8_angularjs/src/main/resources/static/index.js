// $scope - хранит всё, до чего можно достучаться в html-е;
// $http - через неё можно делать запросы на наш бэкенд
angular.module('app', []).controller('indexController', function ($scope, $http) {

    const contextPath = 'http://localhost:8088/api/v1';

    $scope.logout = function () {
        $http.get('/logout');
    };

    $scope.fillTable = function (pageIndex = 1) {
        $http({
            url: contextPath + '/product',
            method: 'GET',
            params: {
                title: $scope.filter ? $scope.filter.title : null,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null,
                p: pageIndex
            }
        }).then(function (response) {
            $scope.ProductsPage = response.data;

            let minPageIndex = pageIndex - 2;
            if (minPageIndex < 1) {
                minPageIndex = 1;
            }

            let maxPageIndex = pageIndex + 2;
            if (maxPageIndex > $scope.ProductsPage.totalpages) {
                maxPageIndex = $scope.ProductsPage.totalpages;
            }

            $scope.PaginationArray = $scope.generatePagesIndexes(minPageIndex, maxPageIndex);
        });
    };

    $scope.showCart = function () {
        $http({
            url: contextPath + '/cart',
            method: 'GET'
        }).then(function (response) {
            $scope.Cart = response.data;
        });
    };

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    };

    $scope.submitCreateNewProduct = function () {
        $http.post(contextPath + '/products', $scope.newProduct)
                .then(function (response) {
                    $scope.newProduct = null;
                    $scope.fillTable();
                });
    };

    $scope.deleteProductById = function (productId) {
        $http.delete(contextPath + '/products/', productId)
                .then(function (response) {
                    $scope.fillTable();
                });
    };

    $scope.addToCart = function (productId) {
        console.log(productId);
        const url = contextPath + '/cart?productId=' + productId;
        console.log(url);
        $http.put(url)
                .then(function (response) {
                    $scope.showCart();
                });
    };

    $scope.clearCart = function () {
        $http.delete(contextPath + '/cart')
                .then(function (response) {
                    $scope.showCart();
                });
    };

    $scope.fillTable();
    $scope.showCart();

});
