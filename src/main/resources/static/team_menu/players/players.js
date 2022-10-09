angular.module('players', ['ngStorage']).controller('playersController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';


    $scope.loadPlayersSort = function (parameter) {
        $http.get(contextPath + '/team/players/' + parameter)
            .then(function successCallback(response) {
                $scope.Players = response.data;
            }, function errorCallback() {
                alert('PLAYERS OF YOUR TEAM NOT FOUND');
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

    $scope.getUserTeamName = function () {
        $http.get(contextPath + '/team/name')
            .then(function successCallback(response) {
                $scope.team = response.data;
            }, function errorCallback() {
                alert('TEAM NAME NOT FOUND');
            });
    };

    $scope.getInfoAboutPlayer = function (name) {
        $http.get(contextPath + '/player/' + name)
            .then(function successCallback(response) {
                $localStorage.rootPlayer = response.data;
                window.location.href = '../player_window/player_window.html';
            }, function errorCallback() {
                alert('PLAYERS WITH NAME ' + name + ' NOT FOUND');
            });
    };


    $scope.getActualDate();
    $scope.loadPlayersSort(0);
    $scope.getUserTeamName();
});