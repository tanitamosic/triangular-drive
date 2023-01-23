class Photo {
  id: Number = 0;
  path: String = '';

}

export class User {

  id: Number = 0;

  name: String = '';
  lastName: String = '';
  email: String = '';
  phone: String = '';
  city: String = '';
  blocked: boolean = true;
  activated: boolean = false;
  photo: Photo | undefined;

  accessToken: String = '';
  role: String = '';

  constructor(obj: String) {
    obj && Object.assign(this, JSON.parse(obj.toString()));
  }

  getPhoto() {
    return <String>this.photo?.path;
  }

}
