angular.module('expenses', ['ngStorage']).controller('expensesController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';


    $scope.loadExpensesInfo = function () {
        $http.get(contextPath + '/team/finance/expenses')
            .then(function successCallback(response) {
                $scope.expenses = response.data;
            }, function errorCallback(response) {
                alert('CLUB EXPENSES NOT FOUND');
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


    $scope.getActualDate();
    $scope.loadExpensesInfo();
    $scope.getUserTeamName();
});