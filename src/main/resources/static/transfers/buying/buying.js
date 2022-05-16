angular.module('buying', ['ngStorage']).controller('buyingController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';

    $scope.getUserTeamName = function () {
        $http.get(contextPath + '/team/name')
            .then(function successCallback(response) {
                $scope.team = response.data;
                $scope.loadUserTeamNextPlayersSort(-1, 0);
            }, function errorCallback(response) {
                alert('TEAM NAME NOT FOUND');
            });
    };


    $scope.loadOpponentNextPlayersSort = function (name, playerParameter, sortParameter) {
        $scope.opponentTeam.playerParameter = playerParameter;
        $scope.opponentTeam.sortParameter = sortParameter;
        $http.put(contextPath + '/team/'+ name + '/players/next/' + sortParameter, playerParameter)
            .then(function successCallback(response) {
                $scope.opponentPlayers = response.data;
                $scope.opponentTeam.playerParameter += 1
                if($scope.opponentTeam.playerParameter > $scope.opponentTeam.teamFullSize - 10){
                    $scope.opponentTeam.playerParameter = $scope.opponentTeam.teamFullSize - 10;
                }
            }, function errorCallback(response) {
                alert('OPPONENT PLAYERS NOT FOUND');
            });
    };

    $scope.loadOpponentPrevPlayersSort = function (name, playerParameter, sortParameter) {
            $scope.opponentTeam.playerParameter = playerParameter;
            $scope.opponentTeam.sortParameter = sortParameter;
        $http.put(contextPath + '/team/'+ name + '/players/prev/' + sortParameter, playerParameter)
            .then(function successCallback(response) {
                $scope.opponentPlayers = response.data;
                $scope.opponentTeam.playerParameter -= 1;
                if($scope.opponentTeam.playerParameter == -1){
                    $scope.opponentTeam.playerParameter = 0;
                }
            }, function errorCallback(response) {
                alert('OPPONENT PLAYERS NOT FOUND');
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

    $scope.loadUserTeamNextPlayersSort = function (parameter, sortParameter) {
        $scope.team.playerParameter = parameter;
        $scope.team.sortParameter = sortParameter;
        $http.put(contextPath + '/team/userteam/players/next/' + sortParameter, parameter)
            .then(function successCallback(response) {
                $scope.Players = response.data;
                $scope.team.playerParameter += 1
                if($scope.team.playerParameter > $scope.team.teamFullSize - 10){
                    $scope.team.playerParameter = $scope.team.teamFullSize - 10;
                }
            }, function errorCallback(response) {
                alert('PLAYERS OF YOUR TEAM NOT FOUND');
            });
    };

    $scope.loadUserTeamPrevPlayersSort = function (parameter, sortParameter) {
        $scope.team.playerParameter = parameter;
        $scope.team.sortParameter = sortParameter;
        $http.put(contextPath + '/team/userteam/players/prev/' + sortParameter, parameter)
            .then(function successCallback(response) {
                $scope.Players = response.data;
                $scope.team.playerParameter -= 1;
                if($scope.team.playerParameter == -1){
                    $scope.team.playerParameter = 0;
                }
            }, function errorCallback(response) {
                alert('PLAYERS OF YOUR TEAM NOT FOUND');
            });
    };

    $scope.getPrevOppenentTeamName = function (parameter) {
        $http.get(contextPath + '/team/name/prev/' + parameter)
            .then(function successCallback(response) {
                $scope.opponentTeam = response.data;
                $scope.loadOpponentPrevPlayersSort($scope.opponentTeam.name, $scope.opponentTeam.playerParameter, 0);
            }, function errorCallback(response) {
                alert('OPPONENT TEAM NAME NOT FOUND');
            });
    };

    $scope.getNextOppenentTeamName = function (parameter) {
        $http.get(contextPath + '/team/name/next/' + parameter)
            .then(function successCallback(response) {
                $scope.opponentTeam = response.data;
                $scope.loadOpponentNextPlayersSort($scope.opponentTeam.name, $scope.opponentTeam.playerParameter, 0);
            }, function errorCallback(response) {
                alert('OPPONENT TEAM NAME NOT FOUND');
            });
    };



    $scope.getActualDate();
    $scope.getUserTeamName();
    $scope.getNextOppenentTeamName(-1);
});