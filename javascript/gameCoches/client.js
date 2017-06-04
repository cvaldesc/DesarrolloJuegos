var GraphicsContext; //contexto de dibujo canvas
var Cars = []; //todos los datos de los coches
var MyCar = null; //referencia a ese eleento de cars que controla el juegador
var KeysPressed = 0; //bit 0:up bit 1:izq bit 2:der

var CarImage = new Image();
CarImage.src = "car.png";

document.addEventListener("keydown", function (E) {
	//arriba
	if(E.which == 38 && (KeysPressed & 1) == 0){
		 KeysPressed |= 1;
	}
	//izq
	else if(E.which == 37 && (KeysPressed & 2 ) == 0 ){
		 KeysPressed |= 2;
	}
	//der
	else if(E.which == 39 && (KeysPressed & 4 ) == 0 ){
		 KeysPressed |= 4;
	}
});

document.addEventListener("keyup", function (E) {
	//arriba
	if(E.which == 38){
		KeysPressed &= ~1;
	} 
	//izq
	else if(E.which == 37 ){
		 KeysPressed &= ~2;
	}
	//der
	else if(E.which == 39 ){
		KeysPressed &= ~4;	
	} 
});
window.addEventListener("load", function () {
	var BumperCanvas = document.getElementById("BumperCanvas");
	BumperCanvas.width = GP.GameWidth;
	BumperCanvas.height = GP.GameHeight;
	GraphicsContext = BumperCanvas.getContext("2d");

	//configura el bucle del juego
	setInterval(function () {
		/* body... */
		if(MyCar){
			//Gira a la izquierda
			if(KeysPressed & 2) {
				MyCar.OR -= GP.TurnSpeed;
			}
			//gira a la derecha
			if(KeysPressed & 4) {
				MyCar.OR += GP.TurnSpeed;
			}
			//acelera
			if(KeysPressed & 1){
				MyCar.VX += GP.Acceleration * Math.sin(MyCar.OR);//averiguar que es OR
				MyCar.VY -= GP.Acceleration * Math.cos(MyCar.OR);//averiguar sin y cos
			}
		}
		RunGameFrame(Cars);
		DrawGame();
	}, GP.GameFrameTime);
});

function DrawGame () {
	//Limpia la pantalla
	GraphicsContext.clearRect(0,0,GP.GameWidth, GP.GameHeight);
	for (var i = 0; i < Cars.length; i++) {
		GraphicsContext.save();
		GraphicsContext.translate(Cars[i].X | 0, Cars[i].Y | 0);
		GraphicsContext.rotate(Cars[i].OR);
		GraphicsContext.drawImage(CarImage,-CarImage.width / 2 | 0, CarImage.height / 2 | 0);
		GraphicsContext.restore();
	};
}
