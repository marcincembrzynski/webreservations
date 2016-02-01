<div ng-controller="timesController">

    <ul class="availabilityTimes">
        <li ng-repeat="time in times" class="{{time === selectedTime ? 'selected' : ''}}">
            <p >
                <input type="radio" name="selectedTime" value="selectedTime"  ng-click="select(time)" />
                {{time.localDateTime}},
                {{time.specialist.firstName}} {{time.specialist.lastName}}, 
                price: {{time.service.price}}
            </p>
        </li>

        <button ng-show="selectedTime !== undefined" ng-click="confirm()">Confirm</button>

     
    </ul>
</div>