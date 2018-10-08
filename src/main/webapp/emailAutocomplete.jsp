  <div class="autocomplete">
  <input class="inputtext form-control" type="text" id="emailTo" ng-model="emailTo" placeholder="Please enter Email addresses " ng-keyup="filterItems()" /> 
    
  <div class="choices" ng-show="isVisible.suggestions">
    <div class="choice" ng-repeat="choice in filteredChoices"
      ng-click="selectItem(choice)">{{choice}}</div>
  </div>
</div>