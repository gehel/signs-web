/*
 * Copyright © ${project.inceptionYear} Guillaume Lederrey <guillaume.lederrey@gmail.com> (${email})
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
