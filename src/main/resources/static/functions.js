
function addPoint(line) {
    var newMessage = ` <div class="point">
  <div>
    Point X: <input type="number" class="pointX" required />
  </div>
  <div>
    Point Y: <input type="number" class="pointY" required />
  </div>
  <input type="button" onclick="deletePoint(this)" value="Delete point" />
</div>`;
    var place = document.querySelector(".points");
    line.parentElement.insertAdjacentHTML("beforeend", newMessage);
};

function addLine() {
    var newMessage = `
<div class="line">
  <div>
    <div>Line</div>
  </div>
  <input type="button" value="Add point" onclick="addPoint(this)">
  <div class="points">
    <div class="point">
      <div>Point X: <input type="number" class="pointX" required />
      </div>
      <div>Point Y: <input type="number" class="pointY" required />
      </div>
    </div>
    <div class="point" name="point">
      <div>Point X: <input type="number" class="pointX" required />
      </div>
      <div>Point Y: <input type="number" class="pointY" required />
      </div>
    </div>
  </div>
</div> `;
    var place = document.querySelector(".lines");
    place.insertAdjacentHTML("beforeend", newMessage);
};

function deletePoint(point) {
    point.parentElement.remove();
};

function sendData () {
    var $myForm = document.querySelector('form');
    if (!$myForm.checkValidity()) {
        $myForm.querySelector('.submit').click();
        return;
    }

    var linesDOM = document.querySelectorAll(".line");
    if(!linesDOM)return;
    var lines=[];
    for(let i=0; i<linesDOM.length; i++){
        var points = [];
        const lineDOM=linesDOM[i];
        var pointsDOM = lineDOM.querySelectorAll(".point");
        for(let j=0; j<pointsDOM.length; j++){
            const pointDOM = pointsDOM[j];
            const pointX = pointDOM.querySelector(".pointX").value;
            const pointY = pointDOM.querySelector(".pointY").value;
            var point = {
                'pointNumber': j,
                'pointX':pointX,
                'pointY':pointY
            };
            points.push(point);
        }

        const line={
            'lineNumber': i,
            'points':points
        };
        lines.push(line);
    }

    var data = {"lines": lines};


    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/image",
        data: JSON.stringify(data),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            alert("success!");
        },
        error: function (e) {
            if(e["status"]==200){
                window.location = e["responseText"];
            } else{
                alert(JSON.stringify(e));
            }
        }
    });
}
