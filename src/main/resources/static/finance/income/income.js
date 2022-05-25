angular.module('income', ['ngStorage']).controller('incomeController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';


    $scope.loadIncomeInfo = function () {
        $http.get(contextPath + '/team/finance/income')
            .then(function successCallback(response) {
                $scope.finance = response.data;
            }, function errorCallback(response) {
                alert('CLUB INCOMES NOT FOUND');
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
    $scope.loadIncomeInfo();
    $scope.getUserTeamName();
});