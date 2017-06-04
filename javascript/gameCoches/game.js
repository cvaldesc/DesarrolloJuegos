var GP = {
	GameWidth: 700,
	GameHeight: 400,
	GameFrameTime: 20,
	CarRadius: 25,
	FrictionMultiplier: 0.97,
	MaxSpeed: 6,
	TurnSpeed: 0.1,
	Acceleration: 0.3
};
function RunGameFrame(Cars) {
	console.log(Cars)
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
			if( (Cars[i].X <= GP.CarRadius && Cars[i].VX <= 0) || (Cars[i].X >= GP.GameWidth - GP.CarRadius && Cars[i].VX >= 0)){
				//Gira el coche
				Impulses.push([i,null, 2 * Cars[i].VX, 0]);
			}

			//hace las paredes muy rigidas. si el coche se estrella, rebota.
			if(Cars[i].X <= GP.CarRadius){
				Cars[i].X = GP.CarRadius;
			}
			if(Cars[i].X >= GP.GameWidth - GP.CarRadius){
				Cars[i].X = GP.GameWidth - GP.CarRadius;
			}

		} 
		//lo mismo de antes, pero ahora las paredes de arriba y abajo.
		if(Cars[i].Y <= GP.CarRadius || Cars[i].Y >= GP.GameHeight - GP.CarRadius){
			if ((Cars[i].Y <= GP.CarRadius && Cars[i].VY <= 0) || (Cars[i].Y >= GP.GameHeight - GP.CarRadius && Cars[i].VY >= 0)) {
				Impulses.push([i, null, 0,2* Cars[i].VY]);
			} 
			if(Cars[i].Y <= GP.CarRadius){
				Cars[i].Y = GP.CarRadius;
			}
			if(Cars[i].Y >= GP.GameHeight - GP.CarRadius){
				Cars[i].Y = GP.GameHeight - GP.CarRadius;
			}
		}
		/*
			ya ha contado los choques con las paredes, ahora busca colisiones
			entre coches. Ds coches chocan si sus centros estan dentro dentro
			2 * GP.CarRadius, por ejemplo. si se superponen.
			observe los limites de este bucle. No hace fata comprobar todos los coches
		*/
		for (var j = i + 1 ; j < Cars.length; j++) {
			//distancia auclidea entre los centros de los dos coches
			var DistSqr = (Cars[i].X - Cars[j].X) * (Cars[i].X - Cars[j].X)+(Cars[i].Y - Cars[j].Y) * (Cars[i].Y - Cars[j].Y);
			if(Math.sqrt(DistSqr) <= 2 * GP.CarRadius){
				//los impulsos de una colision elastica bidimensional
				// delta = (r_j - r_i) * (v_i - v_j)/ |r_j - r_i|^2
				// impulso 1 = -delta * [DX, DY];
				// impulso 2 = delta * [DX, DY];
				var DX = Cars[j].X - Cars[i].X;
				var DY = Cars[j].Y - Cars[i].Y;

				var Delta = (DX * (Cars[i].VX - Cars[i].VX) + DY *(Cars[i].VY - Cars[i].VY))/( DX * DX + DY * DY);

				//si se alejan de la colision
				//(r_j - r_i) * (v_i - v_j) <= 0
				//ya hemos tratado con el choque. esto es similar a la 
				//consideracion sobre el choque contra la pared
				if(Delta <= 0){
					continue;
				}
				Impulses.push([i,j,Delta * DX, Delta * DY]);
			} 
		}
	}
	//Aplica impulsos 
	for (var i = 0; i < Impulses.length; i++) {
		//los choques con la pared especifican cero para uno de los indices de coche,
		//ya que no hay un segundo coche implicado. Asi pues,
		//tendremos cuidado de no hacer referencias a un indice que no pertenezca a la matriz cars
		if(Impulses[i][0] in Cars){
			Cars[Impulses[i][0]].VX -= Impulses[i][2];
			Cars[Impulses[i][0]].VY -= Impulses[i][3];
		}
		if(Impulses[i][1] in Cars){
			Cars[Impulses[i][1]].VX += Impulses[i][2];
			Cars[Impulses[i][1]].VY += Impulses[i][3];
		}
	}
	//Fuerza un limite de velocidades y aplica friccion
	for (var i = 0; i < Cars.length; i++) {
		//reduce la velocidad del coche si sobrepasa el limite.
		var Speed = Math.sqrt(Cars[i].VX * Cars[i].VX + Cars[i].VY * Cars[i].VY);
		if(Speed >= GP.MaxSpeed){
			Cars[i].VX *= GP.MaxSpeed / Speed;
			Cars[i].VY *= GP.MaxSpeed / Speed;
		}
		// la friccion actuara siempre sobre los coches, haciendo que necesiten un descanso
		Cars[i].VX *= GP.FrictionMultiplier;
		Cars[i].VY *= GP.FrictionMultiplier;
	}
	if(typeof exports !== "undefined"){
		exports.GP = GP;
		exports.RunGameFrame = RunGameFrame;
	}

}