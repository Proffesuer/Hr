<template>
    <div :id="id" ref="editorRef"/>
</template>

<script>
    // deps for editor
    import 'codemirror/lib/codemirror.css' // codemirror
    import 'tui-editor/dist/tui-editor.css' // editor ui
    import 'tui-editor/dist/tui-editor-contents.css' // editor content
    import Editor from '@toast-ui/editor'
    import defaultOptions from './default-options'
    import colorSyntax from '@toast-ui/editor-plugin-color-syntax';
    export default {
        name: 'MarkdownEditor',
        props: {
            value: {
                type: String,
                default: ""
            },
            id: {
                type: String,
                required: false,
                default() {
                    return 'markdown-editor-' + +new Date() + ((Math.random() * 1000).toFixed(0) + '')
                }
            },
            options: {
                type: Object,
                default() {
                    return defaultOptions
                }
            },
            mode: {
                type: String,
                default: 'markdown'
            },
            height: {
                type: String,
                required: false,
                default: '400px'
            },
            language: {
                type: String,
                required: false,
                default: 'en_US' // https://github.com/nhnent/tui.editor/tree/master/src/js/langs
            }
        },
        data() {
            return {
                editor: null
            }
        },
        computed: {
            editorOptions() {
                const options = Object.assign({}, defaultOptions, this.options)
                options.initialEditType = this.mode
                // options.previewStyle = 'tab'
                options.height = this.height
                options.language = this.language
                return options
            }
        },
        watch: {
            value(newValue, preValue) {
                if (newValue !== preValue && newValue !== this.editor.getMarkdown()) {
                    this.invoke("setMarkdown",newValue)
                }
            },
            language(val) {
                this.destroyEditor()
                this.initEditor()
            },
            height(newValue) {
                this.editor.height(newValue)
            },
            mode(newValue) {
                this.editor.changeMode(newValue)
            }
        },
        mounted() {
            this.initEditor()
        },
        destroyed() {
            this.destroyEditor()
        },
        methods: {
            invoke(methodName, ...args) {
                let result = null;
                if (this.editor[methodName]) {
                    result = this.editor[methodName](...args);
                }

                return result;
            },

            initEditor() {
                let vm = this;
                this.editor = new Editor({
                    el: vm.$refs.editorRef,
                    //initialValue: vm.value,
                    //document.getElementById(this.id),
                    ...this.editorOptions,
                    plugins:[colorSyntax]
                })
                if (this.value) {
                    console.log("editor value is ", this.value);
                    this.invoke("setMarkdown",this.value)
                   // this.editor.setValue(this.value)
                }
                this.editor.on('change', () => {
                    this.$emit('input', this.editor.getMarkdown())
                })
            },
            destroyEditor() {
                if (!this.editor) return
                this.editor.off('change')
                this.editor.remove()
            },
            setValue(value) {
                this.invoke("setMarkdown",value)
                //this.editor.setValue(value)
            },
            getValue() {
                return this.editor.getMarkdown()
            },
            setHtml(value) {
                this.editor.setHtml(value)
            },
            getHtml() {
                return this.editor.getHtml()
            }
        }
    }
</script>