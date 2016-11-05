angular.module('signs', [])
    .controller('SearchController', function ($scope) {
        $scope.signs = [
            {
                "name": "angular hello",
                "description": "A greeting",
                "image": "000000"
            },
            {
                "name": "angular eat",
                "description": "getting food",
                "image": "333333"
            },
            {
                "name": "angular car",
                "description": "Locomotion",
                "image": "666666"
            },
            {
                "name": "angular train",
                "description": "Locomotion",
                "image": "999999"
            },
            {
                "name": "angular good bye",
                "description": "When leaving",
                "image": "CCCCCC"
            },
            {
                "name": "angular banane",
                "description": "a fruit",
                "image": "FFFFFF"
            }
        ];
    });
