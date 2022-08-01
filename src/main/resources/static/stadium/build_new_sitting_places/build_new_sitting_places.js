angular.module('buildNewSittingPlaces', ['ngStorage']).controller('buildNewSittingPlacesController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';


    $scope.getFullCapacityInformation = function () {
        $http.get(contextPath + '/team/stadium/capacity')
            .then(function successCallback(response) {
                $scope.Information = response.data;
            }, function errorCallback(response) {
                alert('CANT GET FULL CAPACITY INFORMATION');
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
            }, function errorCallback() {
                alert('TEAM NAME NOT FOUND');
            });
    };

    $scope.expandTheStadium = function (delta) {
        $http.post(contextPath + '/team/stadium/expand', delta)
            .then(function successCallback() {
                $scope.getUserTeamName();
                $scope.getFullCapacityInformation();
            }, function errorCallback() {
                alert('EXPAND THA STADIUM IS NOT AVALIBLE');
            });
    };

    $scope.getActualDate();
    $scope.getUserTeamName();
    $scope.getFullCapacityInformation();
});