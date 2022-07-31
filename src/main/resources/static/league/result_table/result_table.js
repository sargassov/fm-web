angular.module('result_table', ['ngStorage']).controller('result_tableController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';

    $scope.loadResultMatrix = function () {
        $http.get(contextPath + '/league/results')
            .then(function successCallback(response) {
                $scope.Cortage = response.data;
            }, function errorCallback() {
                alert('CANT LOAD RESULTS MATRIX');
            });
    };

    $scope.getActualDate = function () {
        $http.get(contextPath + '/dates')
            .then(function successCallback(response) {
                $scope.today = response.data;
            }, function errorCallback() {
                alert('PRESENT DAY NOT FOUND');
            });
    };


    $scope.getActualDate();
    $scope.loadResultMatrix();
});