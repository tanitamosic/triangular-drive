export class CarType {
  name: String = '';
  code: String = '';

  constructor(enumString: String) {
    this.code = enumString;
    this.name = enumString.charAt(0).toUpperCase() + enumString.slice(1).toLowerCase(); // Capitalize first letter, and lowercase rest
  }
}


export class CarMaker {
  name: String = '';
  code: String = '';

  constructor(enumString: String) {
    this.code = enumString;
    this.name = enumString.charAt(0).toUpperCase() + enumString.slice(1).toLowerCase(); // Capitalize first letter, and lowercase rest
  }

}

export class Color {
  name: String = '';
  code: String = '';

  constructor(enumString: String) {
    this.code = enumString;
    this.name = enumString.charAt(0).toUpperCase() + enumString.slice(1).toLowerCase(); // Capitalize first letter, and lowercase rest
  }

}

export class Car {
  id: number = 0;
  model: string="";
  make:CarMaker= new CarMaker("");
  seats:number = 0;
  color:Color = new Color("");
  babyFriendly: boolean = false;
  petFriendly: boolean = false;
  type:CarType = new CarType("");
  position:number[]=[0,0];
  destination:number[]=[0,0];

  constructor(car:any){
    this.id=car.id;
    this.model=car.model;
    this.make=car.make;
    this.seats=car.seats;
    this.color=car.color;
    this.babyFriendly=car.babyFriendly;
    this.petFriendly=car.petFriendly;
    this.type=car.type;
    this.position=[0,0];
    this.destination=[0,0];
  }

}
