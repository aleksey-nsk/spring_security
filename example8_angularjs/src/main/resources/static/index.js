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

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    };

    $scope.fillTable();

});
