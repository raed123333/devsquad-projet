export interface Employee {
  id: number;
  nom: string;
  prenom: string;
  email: string;
  cin: number;
  age: number;
  gender: string;
  phoneNumber: number;
  poste: string;
  password: string;
  etat: boolean;
  role: 'ADMIN' | 'EMPLOYEE' | 'RH';
  isValidated: boolean;
}

export interface AuthResponse {
  token: string;
  user: {
    email: string;
  };
}

export interface LoginCredentials {
  email: string;
  password: string;
}

export interface RegisterData {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  department: string;
  position: string;
  gender: string;
}