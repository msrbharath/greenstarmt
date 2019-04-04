import { AbstractControl, FormControl, FormGroup } from "@angular/forms";

export class ValidatorUtil {

    /**  
     * If the form is not valid during onSubmit this method will be called for validation  
     * @param  {FormGroup} formGroup  
     */

    public static validateAllFormFields(formGroup: FormGroup): void {
        Object.keys(formGroup.controls).forEach(field => {
            const control: AbstractControl = formGroup.get(field);
            if (control instanceof FormControl) {
                control.markAsTouched({ onlySelf: true });
            } else if (control instanceof FormGroup) {
                this.validateAllFormFields(control);
            }

        });
    }

    /**  
     * To check if a field in rate exhibits screen is valid  
     * @param  {string} field  
     */

    public static isFieldValid(formGroup: FormGroup, field: string): boolean {

        return !formGroup.get(field).valid && formGroup.get(field).touched;

    }

    /**  
     * Method to display error message if the field is not valid  
     * @param  {string} field  
     */

    public static displayFieldCss(formGroup: FormGroup, field: string): Object {
        return {
            'is-valid': this.isFieldValid(formGroup, field),
            'is-invalid': this.isFieldValid(formGroup, field)
        };
    }

    public static isEmpty(value: string) {
        return (typeof value === 'undefined' || value === null || value === '' || value.length <= 0) ? true : false;
    }

}
