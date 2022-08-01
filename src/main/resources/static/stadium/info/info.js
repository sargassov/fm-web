angular.module('info', ['ngStorage']).controller('infoController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';

    $scope.loadStadiumInfo = function () {
        $http.get(contextPath + '/stadium/info')
            .then(function successCallback(response) {
                $scope.Information = response.data;
            }, function errorCallback(response) {
                alert('INFORMATIOM ABOUT STADIUM NOT FOUND');
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
    $scope.getUserTeamName();
    $scope.loadStadiumInfo();
});