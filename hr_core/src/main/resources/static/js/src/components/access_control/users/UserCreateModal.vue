<template>
    <ModalTemplate
            width="960"
            ref="modalTemplate"
            @modalClosed="$router.back()">
            <UserCreateForm
                    :role-id="$route.params.id"
                    @userCreated="onUserCreated"
                    slot="modal-content"></UserCreateForm>
    </ModalTemplate>
</template>
<script>
    import UserCreateForm from "./UserCreateForm";
    import ModalTemplate from "../../common/ModalTemplate";

    export default {
        components: {
            ModalTemplate,
            UserCreateForm
        },
        methods: {
            onUserCreated() {
                this.$emit("userCreated")
                this.$router.back();
            }
        }
    }
</script>