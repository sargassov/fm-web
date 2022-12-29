angular.module('buying', ['ngStorage']).controller('buyingController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';

    $scope.getUserTeamName = function () {
        $http.get(contextPath + '/team/name')
            .then(function successCallback(response) {
                $scope.team = response.data;
                $scope.loadTeamNextPlayersSort($scope.team.id, -1, 0, 1, true);
            }, function errorCallback() {
                alert('TEAM NAME NOT FOUND');
            });
    };

    $scope.loadTeamNextPlayersSort = function (id, playerParameter, sortParameter, delta, userTeam) {
        if (userTeam) {
            $scope.team.playerParameter = playerParameter;
            $scope.team.sortParameter = sortParameter;
        } else {
            $scope.opponentTeam.playerParameter = playerParameter;
            $scope.opponentTeam.sortParameter = sortParameter;
        }
        let info = {
            id: id,
            playerParameter: playerParameter,
            sortParameter: sortParameter,
            delta: delta
        };
        $http.put(contextPath + '/team/transfer/players', info)
            .then(function successCallback(response) {
                if (userTeam) {
                    $scope.Players = response.data;
                    if(delta === 1){
                        $scope.team.playerParameter += 1
                        if($scope.team.playerParameter > $scope.team.teamFullSize - 10){
                            $scope.team.playerParameter = $scope.team.teamFullSize - 10;
                        }
                    }
                    else {
                        $scope.team.playerParameter -= 1;
                        if($scope.team.playerParameter === -1){
                            $scope.team.playerParameter = 0;
                        }
                    }
                } else {
                    $scope.opponentPlayers = response.data;
                    if(delta === 1){
                        $scope.opponentTeam.playerParameter += 1
                        if($scope.opponentTeam.playerParameter > $scope.opponentTeam.teamFullSize - 10){
                            $scope.opponentTeam.playerParameter = $scope.opponentTeam.teamFullSize - 10;
                        }
                    }
                    else {
                        $scope.opponentTeam.playerParameter -= 1;
                        if($scope.opponentTeam.playerParameter === -1){
                            $scope.opponentTeam.playerParameter = 0;
                        }
                    }
                }
            }, function errorCallback() {
                alert('OPPONENT PLAYERS NOT FOUND');
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


    $scope.getNextOppenentTeamName = function (parameter, delta) {
        $http.get(contextPath + '/team/next/players/' + parameter + '/' + delta)
            .then(function successCallback(response) {
                $scope.opponentTeam = response.data;
                $scope.loadTeamNextPlayersSort($scope.opponentTeam.id, $scope.opponentTeam.playerParameter, 0, delta, false);
            }, function errorCallback() {
                alert('OPPONENT TEAM NAME NOT FOUND');
            });
    };

    $scope.buyPlayer = function (o) {
        $http.post(contextPath + '/team/players/buy', o)
            .then(function successCallback(response) {
                alert('YOU HAVE ALREADY BOUGHT ' + o.name + ' IN ' + $scope.team.name + ' FOR ' + o.price + ' mln $');
                $scope.getUserTeamName();
                $scope.getNextOppenentTeamName($scope.opponentTeam.countParameter - 1, 1);
            }, function errorCallback(response) {
                alert('YOU CANT BUY THIS PLAYER');
            });
    };

    $scope.getActualDate();
    $scope.getUserTeamName();
    $scope.getNextOppenentTeamName(-1, 1);
});