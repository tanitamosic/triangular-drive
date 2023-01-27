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

}
