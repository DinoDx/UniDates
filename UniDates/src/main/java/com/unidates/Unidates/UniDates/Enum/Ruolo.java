package com.unidates.Unidates.UniDates.Enum;

public enum Ruolo {
        STUDENTE("STUDENTE"),
        MODERATORE("MODERATORE"),
        COMMUNITY_MANAGER("COMMUNITY_MANAGER");

        private String ruolo;
        private Ruolo(String ruolo){
                this.ruolo = ruolo;
        }

        @Override
        public String toString() {
                return ruolo;
        }
}

