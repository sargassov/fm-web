angular.module('placement', ['ngStorage']).controller('placementController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';

    $scope.loadCurrentPlacement = function () {
        $http.get(contextPath + '/placement/current')
            .then(function successCallback(response) {
                $scope.Placement = response.data;
            }, function errorCallback(response) {
                alert('CURRENT PLACEMENT NOT FOUND');
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

    $scope.selectNewPlacement = function () {
        $http.post(contextPath + '/placement/new', $scope.newPlacement)
            .then(function successCallback(response) {
                $scope.loadCurrentPlacement();
            }, function errorCallback(response) {
                alert('NEW PLACEMENT WAS NOT LOADED');
            });
    };

    $scope.deleteInPlacement = function (number) {
        $http.delete(contextPath + '/placement/delete/' + number)
            .then(function successCallback(response) {
                $scope.loadCurrentPlacement();
            }, function errorCallback(response) {
                alert('DELETING OF ' + number + ' NOT CORRECTING');
            });
    };

    $scope.autoselect = function () {
        $http.get(contextPath + '/placement/current/autoselect')
            .then(function successCallback(response) {
                $scope.loadCurrentPlacement();
            }, function errorCallback(response) {
                alert('TEAM NAME NOT FOUND');
            });
    };


    $scope.getActualDate()
    $scope.loadCurrentPlacement();
    $scope.getUserTeamName();
});