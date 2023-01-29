import {Component, OnInit} from '@angular/core';
import {UserService} from "../user.service";
import {User} from "../model/user.class";
import {ActivatedRoute} from '@angular/router';
import {ProfileService} from "./profile.service";
import {City} from "../model/city.class";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  user: User;
  profile: User;
  profileId: Number = 0;
  newProfileImage: File | undefined;
  img: String = '';
  cities: City[] = [];
  selectedCity: any;
  oldPassword: String = '';
  newPassword1: String = '';
  newPassword2: String = '';


  constructor(private userService: UserService,
              private route: ActivatedRoute,
              private profileService: ProfileService) {
    this.user = this.userService.getUser();
    this.profile = this.userService.getUser();

    this.setImg();

    // LOAD CITIES
    const request = this.profileService.getCitiesRequest();
    request.subscribe((response) => {
      this.cities = response as City[];
      console.log("Cities: " + this.cities)
      this.setSelectedCityByProfile();
    })
  }

  setImg() {
    this.img = this.profile.photo !== null ? this.profile.getPhoto() : '';
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.profileId = Number(params['profileId']);
      // CHECK IF CLIENT IS NOT THE OWNER OF THE PROFILE
      if (this.profileId !== this.user.id) {
        const request = this.profileService.getProfileRequest(this.profileId);
        request.subscribe((response) => {
            this.profile = JSON.parse(response);
            this.setImg();
          },
          (error) => {
            alert("Something went kaboom, teehee. Non existent user id.")
          });
      }
    });
  }

  addImg(e: any) {
    const image = e.target.files[0];
    const reader = new FileReader();
    reader.readAsDataURL(image);
    reader.onload = e => {
      // @ts-ignore
      let previewImage = e.target.result;
      // @ts-ignore
      this.img = String(previewImage);
      this.newProfileImage = image;
    };
  }

  updateProfile() {
    // UPDATE CITY
    this.profile.city = this.selectedCity.code;

    // UPDATE PROFILE IMAGE (IF SELECTED)
    if (this.newProfileImage !== undefined) {
      const request = this.profileService.postImageUploadRequest(this.newProfileImage);
      request.subscribe((response) => {
        // @ts-ignore
        this.img = response.path;
        // @ts-ignore
        this.profile.photo = response;
      }, (error) => {
        alert(error.message);
      });

    }
    this.metadataUpdate();

  }
  metadataUpdate() {
    // UPDATE PROFILE INFO
    const update = this.profileService.postProfileUpdateRequest(this.profile);
    update.subscribe((res) => {
      console.log(res);
      alert(res + "\r\nRelog for changes to take effect");
    }, (err) => {
      alert(err.message)
    });
  }

  setSelectedCityByProfile() {
    this.cities.forEach((c) => {
      if (c.code === this.profile.city) {
        this.selectedCity = c;
        return;
      }
    })
  }

  updatePassword() {
    if (this.oldPassword.length === 0 || this.newPassword1.length === 0 || this.newPassword2.length === 0) {
      alert("Password fields cant be empty");
      return;
    } else if (this.newPassword1 !== this.newPassword2) {
      alert("New passwords aren't matching")
      return;
    } else if (this.newPassword1.length < 6) {
      alert("New password must have minimum length of 6 characters");
      return;
    }

    const request = this.profileService.postChangePasswordRequest(this.oldPassword, this.newPassword1, this.newPassword2, this.user.id);
    request.subscribe((response) => {
      alert(response);
    }, (error) => {
      alert(error.message);
    })
  }

  consumeKey($event: KeyboardEvent, newPassword: String) {
    if (newPassword.length === 16) {
      $event.preventDefault();
    }
  }

}
