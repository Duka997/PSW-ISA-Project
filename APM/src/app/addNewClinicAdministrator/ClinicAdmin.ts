import { Clinic } from '../addNewClinic/clinic';

export class ClinicAdmin {
    constructor(
        private id: string,
        private  name: string,
        private  surname: string,
        private  ucidn: string,
        private  address: string,
        private  city: string,
        private  country: string,
        private  email: string,
        private  phone: string,
        public  clinic: Clinic,
        private  password: string
    ){}

    public getId(){
        return this.id;
    }
}