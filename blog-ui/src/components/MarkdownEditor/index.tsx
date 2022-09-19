import React from 'react';
import SimpleMdeReact from 'react-simplemde-editor';
import "easymde/dist/easymde.min.css";
// import MdEditor from 'md-editor-rt';
// import 'md-editor-rt/lib/style.css';

export type MarkdownEditorProps = {
  content?: string;
  onChange?: (value?: string) => void;
};
const MarkdownEditor: React.FC<MarkdownEditorProps> = (props: MarkdownEditorProps) => {
  const {
    content,
    onChange,
  } = props;

  return (
    <>
      {/*<MdEditor*/}
      {/*  style={{height:"100%"}}*/}
      {/*  modelValue={content || ''}*/}
      {/*  // previewOnly={true}*/}
      {/*  // onChange={onChange}*/}
      {/*/>*/}


      <SimpleMdeReact
        style={{height: "100%"}}
        // options={{toolbar: false}}
        value={content || ''}
        onChange={onChange}
      />

    </>
  );
};

export default MarkdownEditor;
