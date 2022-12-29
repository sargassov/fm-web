angular.module('selling', ['ngStorage']).controller('sellingController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';


    $scope.getSellingList = function () {
        $http.get(contextPath + '/team/players/selllist')
            .then(function successCallback(response) {
                $scope.Players = response.data;
            }, function errorCallback(response) {
                alert('SELLING LIST OF YOUR TEAM NOT FOUND');
            });
    };

    $scope.getActualDate = function () {
        $http.get(contextPath + '/dates')
            .then(function successCallback(response) {
                $scope.today = response.data;
            }, function errorCallback(response) {
                alert('PRESENT DAY NOT FOUND');
            });
    };

    $scope.getUserTeamName = function () {
        $http.get(contextPath + '/team/name')
            .then(function successCallback(response) {
                $scope.team = response.data;
            }, function errorCallback(response) {
                alert('TEAM NAME NOT FOUND');
            });
    };

    $scope.sellForHalfPrice = function (id, price) {
        $http.delete(contextPath + '/team/players/sell/' + id)
            .then(function successCallback() {
                $scope.getActualDate();
                $scope.getSellingList();
                $scope.getUserTeamName();
                alert('PLAYER WITH ID #' + id + ' WAS SOLD FOR ' + price);
            }, function errorCallback(responce) {
                alert('CANT SELL PLAYER');
            });
    };


    $scope.getActualDate();
    $scope.getSellingList();
    $scope.getUserTeamName();
});