angular.module('changes', ['ngStorage']).controller('changesController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';

    $scope.getActualDate = function () {
        $http.get(contextPath + '/dates')
            .then(function successCallback(response) {
                $scope.today = response.data;
            }, function errorCallback(response) {
                alert('PRESENT DAY NOT FOUND');
            });
    };

    $scope.loadChanges = function () {
        $http.get(contextPath + '/new_day/changes')
            .then(function successCallback(response) {
                $scope.information = response.data;
            }, function errorCallback() {
                alert('CANT LOAD CHANGES');
            });
    };


    $scope.getActualDate();
    $scope.loadChanges();
});