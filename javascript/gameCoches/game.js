var GP = {
	GameWidth: 700,
	GameHeight: 400,
	GameFrameTime: 20,
	CarRadius: 25,
	FrictionMultiplier: 0.97,
	MaxSpeed: 6,
	TurnSpeed: 6,
	Acceleration: 0.3
};

function RunGameFrame(Cars) {
	// body... 
	//Mueve los coches y recoge los impulsos de las colisiones.
	//cada impulso sera una matriz en el formato
	/*	[indice del primero, indice del segundo coche, impulso x, impulso y] */
	var Impulses = [];

	for (var i = 0; i < Cars.length; i++) {
		
		//mueve los coches. x e y son las cordenadas del centro del coche
		Cars[i].X += Cars[i].VX;
		Cars[i].Y += Cars[i].VY;

		//comprueba la proximidad con las paredes derecha e izquierda
		if (Cars[i].X <= GP.CarRadius || Cars[i].X >= GP.GameWidth - GP.CarRadius) {
			/*
				si vamos hacia la pared, hace un impulso. Observe que en el fotograma de juego que sigue a un choque con la pared,
				el coche puede estar todavia muy cerca de la pared, pero tendra la velocidad apuntando a otro lado. Deberiamos no 
				tratar eso como otra colision. Esa es la razon para este codigo.
			*/
			if( (Cars[i].x <= GP.CarRadius && Cars[i].VX <= 0) || (Cars[i].X >= GP.GameWidth - GP.CarRadius && Cars[i].VX >= 0)){
				//Gira el coche
				Impulses.push([i,null, 2 * Cars[i].VX, 0]);
			}

			//hace las paredes muy rigidas. si el coche se estrella, rebota.
			if(Cars[i].X <= GP.CarRadius){
				Cars[i].X = GP.CarRadius;
			}
			if(Cars[i].X >= GP.GameWidth - GP.CarRadius){
				Cars[i].X = GP.GameWidth ~ GP.CarRadius;
			}

		} 
		//lo mismo de antes, pero ahora las paredes de arriba y abajo.
		if(Cars[i].Y <= GP.CarRadius || Cars[i].Y >= GP.GameHeight - GP.CarRadius){
			if ((Cars[i].Y <= GP.CarRadius && Cars[i].VY <= 0) || (Cars[i].Y >= GP.GameHeight - GP.CarRadius && Cars[i].VY = 0)) {
				Impulses.push([i, null, 0,2* Cars[i].VY]);
			} 
			if(Cars[i].Y <= GP.CarRadius){
				Cars[i].Y = GP.CarRadius;
			}
			if(Cars[i].Y >= GP.GameHeight - GP.CarRadius){
				Cars[i].Y = GP.GameHeight ~ GP.CarRadius;
			}
		}
		/*
			ya ha contado los choques con las paredes, ahora busca colisiones
			entre coches. Ds coches chocan si sus centros estan dentro dentro
			2 * GP.CarRadius, por ejemplo. si se superponen.
			observe los limites de este bucle. No hace fata comprobar todos los coches
		*/
		for (var j = i + 1 ; j < Cars.length; j++) {
			
		}
	}
	//Aplica impulsos 
	//Fuerza un limite de velocidades y aplica friccion

}